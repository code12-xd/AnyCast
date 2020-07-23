/**
 *  Created by code12, 2020-07-07.
 */
package com.code12.anycast.ViewModel;

import android.app.Application;

import com.code12.anybaseui.Model.DataLoaderFactory;
import com.code12.anybaseui.Model.IDataLoader;
import com.code12.anycast.Model.Local.LocalMediaCollector;
import com.code12.anycast.Model.types.GameInfo;
import com.code12.anycast.Model.types.LocalMediaInfo;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class LocalViewModel extends BaseFragmentViewModel {
    public LocalViewModel(@NonNull Application application) {
        super(application);
    }

    public LocalMediaInfo load() {
        LocalMediaInfo m = new LocalMediaInfo();
        m.setResult(LocalMediaCollector.getLocalMusics());
        return m;
    }

    @Override
    public void clear() {
    }
}
