class day21_1 {
    public static void main(String[] args) {

        String line = "";
        for (char c : line.toCharArray()) {
            
        }
    }

    class Numpad {
        enum Keys {
            // (J, I) or (x, y)
            _1 (0, 2),
            _2 (1, 2),
            _3 (2, 2),
            _4 (0, 1),
            _5 (1, 1),
            _6 (2, 1),
            _7 (0, 0),
            _8 (1, 0),
            _9 (2, 0),
            _0 (1, 3),
            A  (2, 3);

            int I, J;
            Keys (int j, int i) {
                this.J = j;
                this.I = i;
            }
        }

        
    }
    
    class Dirpad {
        enum Keys {
            // (J, I) or (x, y)
            U (1, 0),
            D (1, 1),
            L (0, 1),
            R (2, 1),
            A (2, 0);

            int I, J;
            Keys (int j, int i) {
                this.J = j;
                this.I = i;
            }
        }

        
    }

    class Robot {
        int I, J;
        boolean isNumpad = false; // false = dirpad
        public Robot(boolean isNumpad) {
            this.isNumpad = isNumpad;
        }

        public boolean equals(Numpad other) {
            return (this.I == other.I && this.J == other.J);
        }

        public boolean equals(Dirpad other) {
            return (this.I == other.I && this.J == other.J);
        }

        public Dirpad nextMove(Numpad goal) {
            if (goal.J > this.J) return Dirpad.R;
            if (goal.I < this.I) return Dirpad.U;
            if (goal.I > this.I) return Dirpad.D;
            if (goal.J < this.J) return Dirpad.L;

            return Dirpad.A;
        }

        public Dirpad nextMove(Dirpad goal) {
            if (goal.J > this.J) return Dirpad.R;
            if (goal.I < this.I) return Dirpad.U;
            if (goal.I > this.I) return Dirpad.D;
            if (goal.J < this.J) return Dirpad.L;

            return Dirpad.A;
        }
    }
}
