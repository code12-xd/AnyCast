/**
 * By code12, 2020-07-04.
 */
package com.code12.anycast.View.Fragments;

import com.code12.anycast.ViewModel.IBaseViewModel;

public interface IBaseFragmentView {
    void onLazyLoad();
    void onInvisible();
}
