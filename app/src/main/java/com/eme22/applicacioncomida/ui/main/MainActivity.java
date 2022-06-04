package com.eme22.applicacioncomida.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.eme22.applicacioncomida.R;
import com.eme22.applicacioncomida.data.model.User;
import com.eme22.applicacioncomida.databinding.ActivityMainBinding;
import com.eme22.applicacioncomida.ui.login.LoginActivity;
import com.eme22.applicacioncomida.ui.user.UserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = (User) getIntent().getSerializableExtra(LoginActivity.EXTRA_USER);

        ViewPager2 viewPager = binding.viewPager2;
        TabLayout tabLayout = binding.tabLayout;
        Toolbar toolbar = binding.toolbar;

        setSupportActionBar(toolbar);

        MainStateAdapter pageAdapter = new MainStateAdapter(this);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(4);
        TabLayoutMediator tablm = new TabLayoutMediator(
                tabLayout,
                viewPager,
                (tab, position) -> tab.setText(pageAdapter.getTitle(position))
        );
        tablm.attach();

        for (int i = 0; i < tabLayout.getTabCount() ; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pageAdapter.getTabView(i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.add(R.id.main_fragment, UserFragment.newInstance(), "UserFragment");
        fTransaction.commit();

        return true;
    }
}