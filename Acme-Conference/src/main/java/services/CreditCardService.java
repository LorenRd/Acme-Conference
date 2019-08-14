package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;
import repositories.CreditCardRepository;

@Service
@Transactional
public class CreditCardService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CreditCardRepository creditCardRepository;

	// Simple CRUD Methods
	public void delete(CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
	}

}
