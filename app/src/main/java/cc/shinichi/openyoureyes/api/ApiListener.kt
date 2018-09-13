package cc.shinichi.openyoureyes.api

import com.lzy.okgo.model.Response

abstract class ApiListener : IApiListener {
    override fun start() {
    }

    override fun success(string: String?) {
    }

    override fun error(response: Response<String>?) {
    }

    override fun noNet() {
    }

    override fun finish() {
    }
}