import java.util.ArrayList;
import java.util.List;

public class OwnerManager {
    private List<Owner> owners = new ArrayList<>();

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public Owner findOwner(String phone) {
        for (Owner owner : owners) {
            if (owner.getPhone().equals(phone)) {
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