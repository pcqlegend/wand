package com.pcq;

class BuildParameterRes
{
    private boolean success;
    private String errMsg;
    private Object data;

    public BuildParameterRes(boolean scuccess, Object o)
    {
        this.success = scuccess;
        this.data = o;
    }

    public BuildParameterRes() {}

    public static BuildParameterRes buildFailedRes(boolean scuccess, String errMsg)
    {
        BuildParameterRes res = new BuildParameterRes();
        res.success = false;
        res.errMsg = errMsg;
        return res;
    }

    public boolean isSuccess()
    {
        return this.success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getErrMsg()
    {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }

    public Object getData()
    {
        return this.data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public BuildParameterRes Success(Object o)
    {
        return new BuildParameterRes(true, o);
    }
}
