
public class ball {

	private int b_s, b_y, b_x, b_vx, b_vy;

	public ball() {

		b_s = 15; // size
		b_y = 400 - b_s; // ball top left corner
		b_x = 615 / 2 - b_s; // ball top left corner
		// game starts with velocity of zero
		b_vx = 0;
		b_vy = 0;
	}

	public int getB_x() {
		return b_x;
	}

	public int getB_y() {
		return b_y;
	}

	public int getB_s() {
		return b_s;
	}

	public int getB_vx() {
		return b_vx;
	}

	public int getB_vy() {
		return b_vy;
	}

	public void setB_x(int newB_x) {
		b_x = newB_x;
	}

	public void setB_y(int newB_y) {
		b_y = newB_y;
	}
	
	public void setB_vx(int newB_vx) {
		b_vx = newB_vx;
	}

	public void setB_vy(int newB_vy) {
		b_vy = newB_vy;
	}

	public void move() {
		b_y += b_vy;
		b_x += b_vx;

		if (b_x <= 0) {
			b_vx *= -1;
		}

		if (b_x + b_s >= 615) {
			b_vx *= -1;
		}

		if (b_y <= 0) {
			b_vy *= -1;
		}
	}

}



