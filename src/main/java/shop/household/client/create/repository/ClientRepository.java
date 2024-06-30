package shop.household.client.create.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.household.client.create.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
