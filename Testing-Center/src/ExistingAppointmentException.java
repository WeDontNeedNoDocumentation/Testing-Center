//When a student tries to make an appointment that already exists
public class ExistingAppointmentException extends RuntimeException {

	public ExistingAppointmentException(String string) {
		super(string);
	}

}
