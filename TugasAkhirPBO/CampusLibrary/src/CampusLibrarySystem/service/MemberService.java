package CampusLibrarySystem.service;

import CampusLibrarySystem.model.Member;
import CampusLibrarySystem.repository.MemberRepository;

import java.util.List;

public class MemberService {
    private final MemberRepository memberRepo;

    public MemberService(MemberRepository repo) {
        this.memberRepo = repo;
    }

    public List<Member> getAllMembers() {
        return memberRepo.loadMembers();
    }

    public void addMember(Member m) {
        List<Member> members = getAllMembers();
        members.add(m);
        memberRepo.saveMembers(members);
    }

    public void removeMember(String id) {
        List<Member> members = getAllMembers();
        members.removeIf(m -> m.getId().equals(id));
        memberRepo.saveMembers(members);
    }

    public String generateNextId() {
        return memberRepo.generateNextId(getAllMembers());
    }
}