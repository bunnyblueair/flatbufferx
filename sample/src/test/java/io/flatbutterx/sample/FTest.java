package io.flatbutterx.sample;


import com.google.flatbuffers.FlatBufferBuilder;

import io.flatbutterx.sample.loader.Main.RepoFB;
import io.flatbutterx.sample.loader.Main.ReposListFB;
import io.flatbutterx.sample.loader.Main.UserFB;
import org.junit.Test;
import sun.nio.ByteBuffered;

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
        FlatBufferBuilder fbb = new FlatBufferBuilder(1);
        int uid = User.createUser(fbb, fbb.createString("bunny"), 1);
        int repoOffset = Repo.createRepo(fbb, 123, fbb.createString("repoName"), fbb.createString("https://gitlab.com/FlatBuffersX/flatbufferx"),
                uid, fbb.createString("404"), fbb.createString("test"));

        int vectors = ReposList.createReposVector(fbb, new int[]{repoOffset});
        fbb.finish(vectors);

        System.out.println("done");
        // fbb.f
//        Monster.startPerson(fbb);
//        Monster.addName(fbb, str);
//        int p = Person.endPerson(fbb);

    }
    @Test
    public void flatFBTest() {
        UserFB userFB=new UserFB();
        userFB.id=123L;
        userFB.login="bunny";
        RepoFB repo=new RepoFB();
        repo.owner=userFB;
        repo.description="this is description";
        repo.htmlUrl="https://gitlab.com/FlatBuffersX/flatbufferx";
        repo.name="FlatBuffersX";
        repo.id=111L;
        repo.fullName="FlatBuffersX";
        ReposListFB reposListFB=new ReposListFB();
        reposListFB.repos=new ArrayList();
        reposListFB.repos.add(repo);

        try {
          ByteBuffer byteBuffered= reposListFB.toFlatBuffer(reposListFB);
            System.err.println(new String(byteBuffered.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;
}
