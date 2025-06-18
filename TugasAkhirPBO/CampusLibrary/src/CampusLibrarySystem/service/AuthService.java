package CampusLibrarySystem.service;

import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.repository.MemberRepository;

public class AuthService {
    private final MemberRepository memberRepo;

    public AuthService(MemberRepository repo) {
        this.memberRepo = repo;
    }

    public Member login(String username, String password) {
        for (Member m : memberRepo.loadMembers()) {
            if (m.getUsername().equals(username) && m.getPassword().equals(password)) {
                return m;
            }
        }
        return null;
    }

    public boolean isUsernameTaken(String username) {
        return memberRepo.loadMembers().stream().anyMatch(m -> m.getUsername().equals(username));
    }

    public boolean isEmailTaken(String email) {
        return memberRepo.loadMembers().stream().anyMatch(m -> m.getEmail().equals(email));
    }
}