
public class paddle {

	private int p_w, p_y, p_x, p_vx;
	boolean right, left;

	public paddle() {
		p_w = 110; // width
		p_x = 615 / 2 - 60; // paddle top left corner
		p_y = 560; // paddle top left corner
		right = false;
		left = false;
	}

	public int getP_x() {
		return p_x;
	}

	public int getP_y() {
		return p_y;
	}

	public int getP_w() {
		return p_w;
	}

	public boolean getRight() {
		return right;
	}

	public boolean getLeft() {
		return left;
	}

	public void setP_x(int newP_x) {
		p_x = newP_x;
	}

	public void setP_y(int newP_y) {
		p_y = newP_y;
	}

	public void setP_w(int newP_w) {
		p_w = newP_w;
	}

	public void setRight(boolean newRight) {
		right = newRight;
	}

	public void setLeft(boolean newLeft) {
		left = newLeft;
	}

	public void move() {

		// add a left wall to paddle
		if (p_x <= 0) {
			p_x = 0;
		}

		// add a right wall to paddle
		if (p_x + p_w > 615) {
			p_x = 615 - p_w;
		}

		if (right == true) {
			p_x += 5;
			left = false;
		}

		if (left == true) {
			p_x -= 5;
			right = false;
		}
	}

}



