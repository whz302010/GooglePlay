package com.example.acer_pc.googleplay.fragment;

import java.util.HashMap;

/**
 * Created by acer-pc on 2017/6/2.
 */

public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> mFragmentMap=new HashMap<>();
    public static BaseFragment creatFragment(int pos){
        //增加代码运行效率

        BaseFragment fragment=creatFragment(pos);
        if (fragment==null){
            switch (pos){
                case 0:
                    fragment=new HomeFragment();
                    break;
                case 1:
                    fragment=new AppFragment();
                    break;
                case 2:
                    fragment=new GameFragment();
                    break;
                case 3:
                    fragment=new SubjectFragment();
                    break;
                case 4:
                    fragment=new RecommendFragment();
                    break;
                case 5:
                    fragment=new CategoryFragment();
                    break;
                case 6:
                    fragment=new SortFragment();
                    break;

            }
            mFragmentMap.put(pos,fragment);
        }

        return fragment;
    }
}
