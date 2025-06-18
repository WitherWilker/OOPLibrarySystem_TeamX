package CampusLibrarySystem.auth;

import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.model.Admin;
import CampusLibrarySystem.repository.MemberRepository;

import java.util.List;

public class LoginService {
    private final MemberRepository memberRepo;

    public LoginService(MemberRepository repo) {
        this.memberRepo = repo;
    }

    public Member loginAsMember(String username, String password) {
        List<Member> members = memberRepo.loadMembers();
        for (Member m : members) {
            if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
                return m;
            }
        }
        return null;
    }

    public Admin loginAsAdmin(String username, String password) {
        // Admin hardcoded (optional: bisa nanti dari file admin.csv)
        if (username.equals("admin") && password.equals("admin123")) {
            return new Admin("A001", "Admin", "admin", "admin123", "admin@library.com");
        }
        return null;
    }
}