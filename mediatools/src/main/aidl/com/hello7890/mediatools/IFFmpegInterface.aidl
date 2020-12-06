// IFFmpegInterface.aidl
package com.hello7890.mediatools;

import com.hello7890.mediatools.FuncDes;
import com.hello7890.mediatools.IExcuteListener;

interface IFFmpegInterface {
    void execute(in FuncDes fundes);

    void cancel();

    void register(IExcuteListener listener);

    void unregister(IExcuteListener listener);
}
