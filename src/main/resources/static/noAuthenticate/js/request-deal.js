!function () {
    var showMessage = function (msg) {
        if (window.layer) {
            if (layer.msg) {
                //PC版layer
                layer.msg(msg, {
                    time: 2000 //2秒后自动关闭
                });
            } else {
                //移动版layer
                layer.open({
                    content: msg
                    , skin: 'msg'
                    , time: 2 //2秒后自动关闭
                });
            }
        } else {
            //警告窗
            alert(msg);
        }
    };
    var lockSubmit = {};
    $.fn.extend({
        "formSubmit": function () {
            var debugModel = false;
            var successFunction = null;
            var errorFunction = null;
            var completeFunction = null;
            var lockNumber = null;
            var normalDealWith = null;
            var submitType = null;
            for (var i = 0; i < arguments.length; i++) {
                if (typeof arguments[i] === "function") {
                    if (!successFunction) {
                        successFunction = arguments[i];
                    } else if (!errorFunction) {
                        errorFunction = arguments[i];
                    } else if (!completeFunction) {
                        completeFunction = arguments[i];
                    }
                } else if (typeof arguments[i] === "number") {
                    if (!lockNumber) {
                        lockNumber = arguments[i];
                    }
                } else if (typeof arguments[i] === "string") {
                    if (arguments[i].toUpperCase() === "DEBUG") {
                        debugModel = true;
                    } else if (!submitType) {
                        submitType = arguments[i];
                    }
                } else if (typeof arguments[i] === "boolean") {
                    if (!normalDealWith) {
                        normalDealWith = arguments[i];
                    }
                } else if (typeof arguments[i] === "object") {
                    if (!arguments[i].success && typeof arguments[i].success === "function") {
                        successFunction = arguments[i].success;
                    }
                    if (!arguments[i].error && typeof arguments[i].error === "function") {
                        errorFunction = arguments[i].error;
                    }
                    if (!arguments[i].complete && typeof arguments[i].complete === "function") {
                        completeFunction = arguments[i].complete;
                    }
                    if (!arguments[i].type && typeof arguments[i].type === "string") {
                        submitType = arguments[i].type;
                    }
                    if (!arguments[i].normal && typeof arguments[i].normal === "boolean") {
                        normalDealWith = arguments[i].normal;
                    }
                    if (!arguments[i].lock && typeof arguments[i].success === "number") {
                        lockNumber = arguments[i].lock;
                    }
                }
            }
            if (normalDealWith === null) {
                normalDealWith = true;
            }
            if (debugModel) {
                console.log({
                    success: successFunction,
                    error: errorFunction,
                    complete: completeFunction,
                    lock: lockNumber,
                    normal: normalDealWith,
                    type: submitType
                });
            }
            if (lockNumber) {
                if (lockSubmit[lockNumber]) {
                    return false;
                }
                lockSubmit[lockNumber] = true;
            }
            if (!submitType) {
                submitType = this.attr("method").toUpperCase();
            }
            var successFunctionOriginal = successFunction;
            var errorFunctionOriginal = errorFunction;
            if (normalDealWith) {
                successFunction = function (data) {
                    try {
                        if (data.url) {
                            setTimeout(function () {
                                location.href = data.url;

                            }, 2000);
                        }
                        if (data.message) {
                            try {
                                showMessage(data.message);
                            } catch (e) {
                                console.error(e.message);
                            }
                        }
                        //status为0即为请求成功，才会调用用户处理程序
                        if (data.status === 0) {
                            try {
                                // 封装请求成功/失败事件，防止成功/失败事件报错导致无法正常触发完成事件
                                var arg = arguments;
                                arg[0] = data.data;
                                successFunctionOriginal && successFunctionOriginal.apply(this, arg);
                            } catch (e) {
                                console.error(e.message);
                            }
                        } else if (debugModel) {
                            console.error("因为设置了normal为true，且返回状态码不为0，放弃调用用户设置的success方法。");
                        }
                    } catch (e) {
                        console.error("一般处理程序无法处理该次请求！\n错误信息为：" + e.message);
                    }
                };
                errorFunction = function () {
                    try {
                        showMessage("网络错误，请稍后重试！");
                    } catch (e) {
                        console.error("一般处理程序无法处理该次请求！\n错误信息为：" + e.message);
                    }
                    try {
                        // 封装请求成功/失败事件，防止成功/失败事件报错导致无法正常触发完成事件
                        errorFunctionOriginal && errorFunctionOriginal.apply(this, arguments);
                    } catch (e) {
                        console.error(e.message);
                    }
                }
            } else {
                successFunction = function () {
                    try {
                        // 封装请求成功/失败事件，防止成功/失败事件报错导致无法正常触发完成事件
                        successFunctionOriginal && successFunctionOriginal.apply(this, arguments);
                    } catch (e) {
                        console.error(e.message);
                    }
                };
                errorFunction = function () {
                    try {
                        // 封装请求成功/失败事件，防止成功/失败事件报错导致无法正常触发完成事件
                        errorFunctionOriginal && errorFunctionOriginal.apply(this, arguments);
                    } catch (e) {
                        console.error(e.message);
                    }
                }
            }
            $.ajax({
                url: this.attr("action"),
                type: submitType,
                data: this.serialize(),
                success: successFunction,
                error: errorFunction,
                complete: function () {
                    try {
                        completeFunction && completeFunction.apply(this, arguments);
                    } catch (e) {
                        console.error(e.message);
                    } finally {
                        delete lockSubmit[lockNumber];
                    }
                }
            });
            return true;
        }
    });
    window.redirect = function (url) {
        if (url && /^#|javasc/.test(url) === false) {
            if (history.replaceState) {
                history.replaceState(null, document.title, url.split('#')[0] + '#');
                location.replace('');
            } else {
                location.replace(url);
            }
        }
    };
}();
