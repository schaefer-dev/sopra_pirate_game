package de.unisaarland.cs.st.pirates.group5.view.utility;

public enum Resolution {
	FULLSCREEN {
	      public String toString() {
	          return "Auto (Fullscreen)";
	      }
	},
	SD {
	      public String toString() {
	          return "480p (Window)";
	      }
	},
	HD {
	      public String toString() {
	          return "720p (Window)";
	      }
	},
	FHD {
	      public String toString() {
	          return "1080p (Window)";
	      }
	}
}


