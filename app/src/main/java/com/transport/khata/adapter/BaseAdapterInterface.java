package com.transport.khata.adapter;

import android.view.View;

public interface BaseAdapterInterface {

    void onClickBtn(View view,String tripId,String tripStatus,Boolean completeBtnFlag);
}
