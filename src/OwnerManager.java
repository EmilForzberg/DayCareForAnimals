import java.util.ArrayList;
import java.util.List;

public class OwnerManager {
    private List<Owner> owners = new ArrayList<>();

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public Owner findOwner(String phone) {
        for (Owner owner : owners) {
            System.out.println("Jämför med: " + owner.getPhone()); // ENDAST FÖR DEBUG
            if (owner.getPhone().trim().equals(phone.trim())) {
                return owner;
            }
        }
        return null;
    }


    // Returnerar en kopia av listan
    public List<Owner> getAllOwners() {
        return new ArrayList<>(owners);
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}