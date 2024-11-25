package ca.mcgill.ecse321.group12.dto;

public class ImageDto {

	private String type;

	private byte[] image;

	@SuppressWarnings("unused")
	private ImageDto() {
	}

	public ImageDto(String type, byte[] image) {
		this.type = type;
		this.image = image;
	}

	public String getType() {
		return type;
	}

	public byte[] getImage() {
		return image;
	}

	public boolean setType(String type) {
		this.type = type;
		return true;
	}

	public boolean setImage(byte[] image) {
		this.image = image;
		return true;
	}

}