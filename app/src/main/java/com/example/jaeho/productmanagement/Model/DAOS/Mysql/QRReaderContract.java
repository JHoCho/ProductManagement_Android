package com.example.jaeho.productmanagement.Model.DAOS.Mysql;

import android.provider.BaseColumns;

/**
 * Created by jaeho on 2017. 9. 4..
 */

public final class QRReaderContract {
    private QRReaderContract(){}
    //테이블 컨텐츠에 대해서 정의합니다
    //베이스 컬럼 인터페이스를 구현함으로써 내부 클래스는 _ID라고 하는 기본 키필드를 상속할 수 있습니다 커서 어댑터와 같은 일부 안드로이드 클래스의 경우 내부 클래스가 이런 기본 키필드를 가지고 있을것이라 예상합니다
    public static class QREntry implements BaseColumns{
        public static final String TABLE_NAME ="entry";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_SUBTITLE="subtitle";
    }
}
