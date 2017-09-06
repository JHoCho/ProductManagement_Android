package com.example.jaeho.productmanagement.utils.QNAActivitys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.jaeho.productmanagement.Model.DO.QNADO;
import com.example.jaeho.productmanagement.R;


/**
 * Created by jaeho on 2017. 8. 3..
 */

public class CustomQNAView extends FrameLayout {
    //동적할당 두번째 단계인 뷰를 설계하는 단계입니다. 매번 들어갈 틀을 설계해줍니다 인플레이트를 동적으로 사용하기위해 설계합니다.
    public CustomQNAView(@NonNull Context context) {
        super(context);
        initData();
    }

    public CustomQNAView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }
    TextView nameTv,subjectTv;
    private void initData(){
        LayoutInflater.from(getContext()).inflate(R.layout.dlg_custom_qna,this);
        nameTv = (TextView)findViewById(R.id.nameTv);
        subjectTv = (TextView)findViewById(R.id.subjectTv);
    }
    public void setData(QNADO qnado){
        nameTv.setText(qnado.getName());
        subjectTv.setText(qnado.getSubject());
    }
}
