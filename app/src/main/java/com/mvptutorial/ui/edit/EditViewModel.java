package com.mvptutorial.ui.edit;

/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import com.mvptutorial.note.BR;

import java.io.Serializable;
import java.util.Objects;

public class EditViewModel extends BaseObservable implements Serializable {
    private String text;

    @Bindable
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }
}
