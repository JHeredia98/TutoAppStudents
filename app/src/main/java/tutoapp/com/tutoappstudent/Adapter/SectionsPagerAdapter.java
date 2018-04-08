package tutoapp.com.tutoappstudent.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tutoapp.com.tutoappstudent.Fragments.ChatsFragment;
import tutoapp.com.tutoappstudent.Fragments.FriendsFragment;
import tutoapp.com.tutoappstudent.Fragments.RequestsFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                RequestsFragment requestsFragment = new RequestsFragment();
                return requestsFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return  chatsFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
            case 0:
                return "SOLICITUDES";

            case 1:
                return "CHATS";

            case 2:
                return "AMIGOS";

            default:
                return null;
        }

    }

}
