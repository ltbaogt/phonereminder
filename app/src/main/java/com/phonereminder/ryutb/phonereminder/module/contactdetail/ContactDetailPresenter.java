package com.phonereminder.ryutb.phonereminder.module.contactdetail;


import com.phonereminder.ryutb.phonereminder.base.BasePresenter;

/**
 * Created by MyPC on 17/05/2017.
 */

public class ContactDetailPresenter extends BasePresenter<ContactDetailMvp.View, ContactDetailMvp.Model> implements ContactDetailMvp.Presenter {

    private static final String TAG = "ContactDetailPresenter";

    public ContactDetailPresenter(ContactDetailMvp.View view, ContactDetailMvp.Model model) {
        super(view, model);
    }

    @Override
    public void renderName() {
        getView().displayName();
    }

    @Override
    public void renderPhone() {
        getView().displayPhone();
    }

    @Override
    public void renderNote(String note) {
        getView().displayNote(note);
    }

    @Override
    public ContactDetailMvp.View createDummyView() {
        return new ContactDetailMvp.View() {
            @Override
            public void displayName() {

            }

            @Override
            public void displayPhone() {

            }

            @Override
            public void displayNote(String note) {

            }

            @Override
            public void setupMvp() {

            }
        };
    }
}