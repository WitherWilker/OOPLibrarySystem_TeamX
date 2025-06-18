package CampusLibrarySystem.repository;

import CampusLibrarySystem.model.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private final String FILE_PATH = "resources/data/members.csv";

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                members.add(new Member(d[0], d[1], d[2], d[3], d[4], d[5]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void saveMembers(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Member m : members) {
                writer.println(String.join(",", m.getId(), m.getName(), m.getMajor(),
                        m.getUsername(), m.getPassword(), m.getEmail()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateNextId(List<Member> members) {
        int max = 0;
        for (Member m : members) {
            String num = m.getId().replace("M", "");
            max = Math.max(max, Integer.parseInt(num));
        }
        return "M" + String.format("%03d", max + 1);
    }
}
