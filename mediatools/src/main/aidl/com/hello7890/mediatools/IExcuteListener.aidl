// IExcuteListener.aidl
package com.hello7890.mediatools;

import com.hello7890.mediatools.FuncDes;

// Declare any non-default types here with import statements
interface IExcuteListener {


    void onProgress(in FuncDes des);

    void complete(in FuncDes des);



}
