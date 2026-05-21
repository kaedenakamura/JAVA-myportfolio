package myportfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ContactTest {

	@Test
	void getCategoryName_returnsRawValueWhenNotNumeric() {
		Contact contact = new Contact();
		contact.setCategory("質問");
		assertEquals("質問", contact.getCategoryName());
	}

	@Test
	void getCategoryName_returnsUnknownWhenBlank() {
		Contact contact = new Contact();
		contact.setCategory("  ");
		assertEquals("不明", contact.getCategoryName());
	}
}
