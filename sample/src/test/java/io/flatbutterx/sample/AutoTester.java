package io.flatbutterx.sample;

//import io.flatbutterx.sample.loader.Main.RepoFB;
//import io.flatbutterx.sample.loader.Main.ReposListFB;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author bunnyblue
 */
public class AutoTester {
    @Test
    public void autoTest() {
        io.flatbutterx.sample.ReposListFB reposListFB = new io.flatbutterx.sample.ReposListFB();
        reposListFB.repos = new ArrayList<>();
        RepoFB repoFB = new RepoFB();
        repoFB.description = "description";
        repoFB.fullName = "fullname";
        repoFB.htmlUrl = "404";
        repoFB.name = "name";
        repoFB.id = 123L;
        UserFB owner = new UserFB();
        owner.id = 456L;
        owner.login = "login";
        repoFB.owner = owner;
        reposListFB.repos.add(repoFB);
        try {
            ByteBuffer byteBuffer = reposListFB.toFlatBuffer(reposListFB);
            System.err.println(new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        //  RepoFB
    }
}
