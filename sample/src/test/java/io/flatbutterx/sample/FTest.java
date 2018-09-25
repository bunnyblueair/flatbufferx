package io.flatbutterx.sample;


import MyGame.Sample.Monster;
import MyGame.Sample.Weapon;
import com.google.flatbuffers.FlatBufferBuilder;
import io.flatbutterx.sample.loader.Main.RepoFB;
import io.flatbutterx.sample.loader.Main.ReposListFB;
import io.flatbutterx.sample.loader.Main.UserFB;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author bunnyblue
 */
public class FTest {
    @Test
    public void flatTest() {
        System.out.println("===ready===");
        FlatBufferBuilder builder = new FlatBufferBuilder(0);

        // Create some weapons for our Monster ('Sword' and 'Axe').
        int weaponOneName = builder.createString("Sword");
        short weaponOneDamage = 3;
        int weaponTwoName = builder.createString("Axe");
        short weaponTwoDamage = 5;

        // Use the `createWeapon()` helper function to create the weapons, since we set every field.
        int[] weaps = new int[2];
        weaps[0] = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
        weaps[1] = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

        // Serialize the FlatBuffer data.
        int name = builder.createString("Orc");
        byte[] treasure = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        //  int inv = Monster.createInventoryVector(builder, treasure);
        int weapons = Monster.createWeaponsVector(builder, weaps);
        //  int pos = Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f);

        if (Boolean.FALSE) {
            Monster.startMonster(builder);
            //  Monster.addPos(builder, pos);
            Monster.addName(builder, name);
            //   Monster.addColor(builder, Color.Red);
            //  Monster.addHp(builder, (short)300);
            //  Monster.addInventory(builder, inv);
            Monster.addWeapons(builder, weapons);
            //   Monster.addEquippedType(builder, Equipment.Weapon);
            //  Monster.addEquipped(builder, weaps[1]);
            int orc = Monster.endMonster(builder);
        } else {
            int orc = Monster.createMonster(builder, name, weapons);
            builder.finish(orc);
        }

        // builder.finish(orc); // You could also call `Monster.finishMonsterBuffer(builder, orc);`.

        ByteBuffer buf = builder.dataBuffer();

        // Get access to the root:
        Monster monster = Monster.getRootAsMonster(buf);
        for (int i = 0; i < monster.weaponsLength(); i++) {
            System.err.println(monster.weapons(i).name());
            //   assert monster.weapons(i).name().equals(expectedWeaponNames[i]);
            //  assert monster.weapons(i).damage() == expectedWeaponDamages[i];
        }

//        int p = Person.endPerson(fbb);

    }

    @Test
    public void flatFBTest() {
        UserFB userFB = new UserFB();
        userFB.id = 123L;
        userFB.login = "bunny";
        RepoFB repo = new RepoFB();
        repo.owner = userFB;
        repo.description = "this is description";
        repo.htmlUrl = "https://gitlab.com/FlatBuffersX/flatbufferx";
        repo.name = "FlatBuffersX";
        repo.id = 111L;
        repo.fullName = "FlatBuffersX";
        ReposListFB reposListFB = new ReposListFB();
        reposListFB.repos = new ArrayList();
        reposListFB.repos.add(repo);
        reposListFB.repos.add(repo);
        ByteBuffer byteBuffered = null;
        try {
            byteBuffered = reposListFB.toFlatBuffer(reposListFB);
            System.err.println(new String(byteBuffered.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("=========");
        // byteBuffered.flip();
        ReposList reposList = ReposList.getRootAsReposList(byteBuffered);
        ReposListFB reposListFB1 = new ReposListFB();
        try {
            reposListFB1.flatBufferToBean(reposList);
            System.err.println(reposListFB1.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void flatTestLis2() {
        System.out.println("===ready===");
        FlatBufferBuilder builder = new FlatBufferBuilder(0);

        // Create some weapons for our Monster ('Sword' and 'Axe').
        int weaponOneName = builder.createString("Sword");
//        short weaponOneDamage = 3;
//        int weaponTwoName = builder.createString("Axe");
//        short weaponTwoDamage = 5;
//
//        // Use the `createWeapon()` helper function to create the weapons, since we set every field.
        int[] weaps = new int[1];
//        weaps[0] = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
//        weaps[1] = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);
        int offset = Repo2.createRepo2(builder, 123L, weaponOneName);
        weaps[0] = offset;
        int weapons = ReposList2.createReposVector(builder, weaps);
        int listOffset = ReposList2.createReposList2(builder, weapons);
        ReposList2.finishReposList2Buffer(builder, listOffset);

        ByteBuffer buf = builder.dataBuffer();

        // Get access to the root:
        ReposList2 monster = ReposList2.getRootAsReposList2(buf);
        for (int i = 0; i < monster.reposLength(); i++) {
            System.err.println(monster.repos(i).name());
            //   assert monster.weapons(i).name().equals(expectedWeaponNames[i]);
            //  assert monster.weapons(i).damage() == expectedWeaponDamages[i];
        }

//        int p = Person.endPerson(fbb);

    }


}
