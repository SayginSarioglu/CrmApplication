package com.example2.diablove.yakamozrehberi.Adapters;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example2.diablove.yakamozrehberi.HelperClasses.Company;
import com.example2.diablove.yakamozrehberi.MenuActivities.Actions;
import com.example2.diablove.yakamozrehberi.MenuActivities.Companies;
import com.example2.diablove.yakamozrehberi.MenuActivities.Gorusmeler;
import com.example2.diablove.yakamozrehberi.MenuActivities.People;
import com.example2.diablove.yakamozrehberi.MenuActivities.Takvimler;
import com.example2.diablove.yakamozrehberi.MenuActivities.Teklifler;
import com.example2.diablove.yakamozrehberi.MenuActivities.Toplantilar;
import com.example2.diablove.yakamozrehberi.R;

/**ksadajskdjaslk
 * deneme15?01
 *  Created by Diablove on 8/24/2016.
 *  1514
 */
public class AlternativeMenuAdapter {

    private AppCompatActivity appCompatActivity;

    public AlternativeMenuAdapter(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    public boolean NavigationBarShort(MenuItem item, AppCompatActivity appCompatActivity) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        this.appCompatActivity = appCompatActivity;

        if (id == R.id.nav_kisiler) {
            Intent myIntent = new Intent(this.appCompatActivity, People.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_firmalar) {
            Intent myIntent = new Intent(this.appCompatActivity, Companies.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_eylemler) {
            Intent myIntent = new Intent(this.appCompatActivity, Actions.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_gorusmeler) {
            Intent myIntent = new Intent(this.appCompatActivity, Gorusmeler.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_toplantilar) {
            Intent myIntent = new Intent(this.appCompatActivity, Toplantilar.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_teklifler) {
            Intent myIntent = new Intent(this.appCompatActivity, Teklifler.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity

        } else if (id == R.id.nav_takvim) {
            Intent myIntent = new Intent(this.appCompatActivity, Takvimler.class);
            this.appCompatActivity.startActivity(myIntent);
            appCompatActivity.finish(); // Call once you redirect to another activity
        }

        DrawerLayout drawer = (DrawerLayout) this.appCompatActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
