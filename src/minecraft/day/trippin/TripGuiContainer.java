package day.trippin;

import java.util.List;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class TripGuiContainer {
	protected final List<TripGuiContainer> children = new ArrayList<>();
	protected final TripGuiLayoutType type;
	protected TripGuiAnchor anchor = TripGuiAnchor.LEFT;
	
	public int width;
	public int height;
	
	public int x;
	public int y;
	
	protected int spacing = 0;
	protected int marginX = 0;
	protected int marginY = 0;
	
	protected int trows = 0;
	
	public boolean visible = true;
	
	public final int id;
	
	public static int IDS = 0;
	
	protected final Minecraft mc;
	
	protected TripGuiContainer lastClickedChild;
	
	public TripGuiContainer(TripGuiLayoutType type, int x, int y, int w, int h) {
		this.id = IDS++;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.mc = Minecraft.getMinecraft();
	}
	
	protected void render(int mx, int my) {
		
	}
	
	public void clearChildren() {
		this.children.clear();
	}
	
	public void draw(int mx, int my) {
		if (!this.visible) {
			return;
		}
		this.render(mx, my);
		for (TripGuiContainer child : this.children) {
			child.draw(mx, my);
		}
	}
	
	public static enum TripGuiLayoutType {
		STATIC,
		DYNAMIC_HORIZONTAL,
		DYNAMIC_VERTICAL,
		GRID
	}
	
	public static enum TripGuiAnchor {
		LEFT,
		RIGHT,
		CENTER
	}
	
	public TripGuiContainer setSpacing(int s) {
		this.spacing = s;
		return this;
	}
	
	public TripGuiContainer setMargainX(int mX) {
		this.marginX = mX;
		return this;
	}
	
	public TripGuiContainer setMargainY(int mY) {
		this.marginY = mY;
		return this;
	}
	
	public void addChild(TripGuiContainer child) {
		this.children.add(child);
	}
	
	public TripGuiContainer build() {
		if (this.type == TripGuiLayoutType.DYNAMIC_HORIZONTAL) {
			int totalWidth = this.width - ((this.marginX * 2) + (this.spacing * (this.children.size() - 1)));
			int totalHeight = this.height - (this.marginY * 2);
			int w = totalWidth / children.size();
			int i = 0;
			for (TripGuiContainer child : this.children) {
				child.x += this.x;
				child.x += i * w;
				child.x += (i == 0 ? this.marginX : this.spacing);
				child.y += this.y;
				child.y += this.marginY;
				child.width = w;
				child.height = totalHeight;
				i++;
			}
		}
		else if (this.type == TripGuiLayoutType.DYNAMIC_VERTICAL) {
			int totalWidth = this.width - (this.marginX * 2);
			int totalHeight = this.height - ((this.marginY * 2) + (this.spacing * (this.children.size() - 1)));
			int w = totalWidth / children.size();
			int i = 0;
			for (TripGuiContainer child : this.children) {
				child.x += this.marginX;
				child.y += i == 0 ? this.marginY : this.spacing;
				child.width = w;
				child.height = totalHeight;
				i++;
			}
		}
		else if (this.type == TripGuiLayoutType.STATIC) {
			for (TripGuiContainer child : this.children) {
				child.x += this.x;
				child.y += this.y;
			}
		} else if (this.type == TripGuiLayoutType.GRID) {
			int row = 0;
			int col = 0;
			int cw = (width - this.marginX * 2 - this.spacing * (this.children.size() - 1)) / trows;
			// int cw = this.children.get(0).width + this.spacing;
			int ch = (children.size() == 0 ? 0 : this.children.get(0).height) + this.spacing; // TODO: fix if none lol
			for (TripGuiContainer child : this.children) {
				if (row >= this.trows) {
					row = 0;
					col++;
				}
				child.width = cw;
				child.x += this.x + this.marginX + (cw + this.spacing) * row;
				child.y += this.y + this.marginY + ch * col;
				row++;
			}
		}
		return this;
	}
	
	protected boolean hovered(int mx, int my) {
		return mx >= this.x && my >= this.y && mx <= this.x + this.width && my <= this.y + this.height;
	}
	
	public boolean acceptClick(int mx, int my) {	
		if (!this.visible) {
			return false;
		}
			
		for (TripGuiContainer ggs : this.children) {
			if (ggs.acceptClick(mx, my)) {
				if (this.lastClickedChild != null && lastClickedChild != ggs) {
					try {
						ggs.onClickOff();
					} catch (Exception e) {
						System.err.println("Exception while clicking off container.");
						e.printStackTrace();
					}
				}
				this.lastClickedChild = ggs;
				return true;
			}
		}
			
		if (this.hovered(mx, my)) {
			try {
				this.onClick();
				return true;
			} catch (Exception e) {
				System.err.println("Exception while clicking container.");
				e.printStackTrace();
			}
		}
		if (lastClickedChild != null) {
			try {
				lastClickedChild.onClickOff();
			} catch (Exception e) {
				System.err.println("Exception while clicking off container.");
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean acceptKey(char c, int k) {
		if (!this.visible) {
			return false;
		}
			
		for (TripGuiContainer ggs : this.children) {
			if (ggs.acceptKey(c, k)) {
				return true;
			}
		}
			
		try {
			if (this.onKey(c, k)) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Exception while typing on container.");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void onClick() throws Exception {
	}
	
	protected void onClickOff() throws Exception {
	}
	
	protected boolean onKey(char character, int keycode) throws Exception {
		return false;
	}
	
	public void update(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public TripGuiContainer setRows(int rows) {
		this.trows = rows;
		return this;
	}
}
