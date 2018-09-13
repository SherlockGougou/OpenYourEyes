package cc.shinichi.openyoureyes.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cc.shinichi.openyoureyesmvp.app.App
import cc.shinichi.openyoureyesmvp.constant.SpTag
import cc.shinichi.openyoureyesmvp.util.ToastUtil
import com.google.gson.Gson

abstract class LazyloadFragment : Fragment() {

    open val TAG: String = javaClass.simpleName
    private var gson: Gson? = null
    private var sp: SharedPreferences? = null

    open fun toast(
            string: String,
            d: Int = Toast.LENGTH_SHORT
    ) {
        if (d == Toast.LENGTH_SHORT) {
            ToastUtil
                    ._short(string)
        } else {
            ToastUtil
                    ._long(string)
        }
    }

    open fun isNull(string: String?): Boolean {
        if (TextUtils.isEmpty(string) || "null" == string || " " == string) {
            return true
        }
        return false
    }

    open fun getGson(): Gson {
        if (gson == null) {
            gson = Gson()
        }
        return gson as Gson
    }

    open fun getSp(): SharedPreferences {
        if (sp == null) {
            sp = App.application.getSharedPreferences(SpTag.defaultSpName, Context.MODE_PRIVATE)
        }
        return sp as SharedPreferences
    }

    /**
     * Fragment title
     */
    var fragmentTitle: String? = null

    /**
     * 是否可见状态 为了避免和[android.support.v4.app.Fragment.isVisible]冲突 换个名字
     */
    private var isFragmentVisible: Boolean = false

    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private var isPrepared: Boolean = false

    /**
     * 是否第一次加载
     */
    private var isFirstLoad = true

    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * 故使用 [BaseLazyloadFragment.setForceLoad]来让Fragment下次执行initData
    </pre> *
     */
    private var forceLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTools()
        val bundle = arguments
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        // 导致initData反复执行,所以这里注释掉
        // isFirstLoad = true;

        // 2016/04/29
        // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
        // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        isFirstLoad = true
        val view = initViews(inflater, container, savedInstanceState)
        isPrepared = true
        lazyLoad()
        return view
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    protected fun onVisible() {
        isFragmentVisible = true
        lazyLoad()
    }

    protected fun onInvisible() {
        isFragmentVisible = false
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected fun lazyLoad() {
        if (isPrepared() && isFragmentVisible()) {
            if (forceLoad || isFirstLoad()) {
                forceLoad = false
                isFirstLoad = false
                initData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
    }

    /**
     * 被ViewPager移出的Fragment 下次显示时会从getArguments()中重新获取数据
     * 所以若需要刷新被移除Fragment内的数据需要重新put数据 eg:
     * Bundle args = getArguments();
     * if (args != null) {
     * args.putParcelable(KEY, info);
     * }
     */
    protected abstract fun initVariables(bundle: Bundle)

    /**
     * 初始化工具类
     */
    protected abstract fun initTools()

    protected abstract fun initViews(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View

    /**
     * 在此处进行耗时操作，比如网络请求
     */
    protected abstract fun initData()

    fun isPrepared(): Boolean {
        return isPrepared
    }

    /**
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     */
    fun setForceLoad(forceLoad: Boolean) {
        this.forceLoad = forceLoad
    }

    fun isFirstLoad(): Boolean {
        return isFirstLoad
    }

    fun isFragmentVisible(): Boolean {
        return isFragmentVisible
    }

    fun getTitle(): String? {
        if (null == fragmentTitle) {
            setDefaultFragmentTitle(null)
        }
        return if (TextUtils.isEmpty(fragmentTitle)) "" else fragmentTitle
    }

    fun setTitle(title: String) {
        fragmentTitle = title
    }

    /**
     * 设置fragment的Title直接调用 [BaseLazyloadFragment.setTitle],若不显示该title 可以不做处理
     *
     * @param title 一般用于显示在TabLayout的标题
     */
    protected abstract fun setDefaultFragmentTitle(title: String?)
}