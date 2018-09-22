package io.flatbuffersx.demo;

import Repos.User;
import Repos.UserFB;
import android.app.Activity;
import android.os.Bundle;
import com.google.flatbuffers.FlatBufferBuilder;

public class FlatBufferTestTmpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_buffer_test_tmp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserFB userFB=new UserFB();
        userFB.id=123L;
        userFB.htmlUrl="hello";
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);
        int weaponOneName = builder.createString("Sword");
       // builder.c
        short weaponOneDamage = 3;
        int weaponTwoName = builder.createString("Axe");
     //   builder.add
        short weaponTwoDamage = 5;
      //  User.createUser(, , , , , , , , , , , , , , , , , )
// Use the `createWeapon()` helper function to create the weapons, since we set every field.
//        int sword = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
//        int axe = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

    }
}
