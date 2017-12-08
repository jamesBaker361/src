public class Player{

	private double bal;
	private double betAmount;
	public Hand cards;
	public boolean folded;
	//declare your fields here
	
	//initialize your fields in the constructor
	public Player(double balance){
		if(balance<0) {
			bal=0;
		}else {
			bal=balance;
		}
		cards=new Hand();
	}
	
	public Player(double balance, Hand h) {
		if(balance<0) {
			bal=0;
		}else {
			bal=balance;
		}
		cards=h;
	}
	
	public Player(double balance, boolean fill) {
		if(balance<0) {
			bal=0;
		}else {
			bal=balance;
		}
		cards=new Hand();
		if(fill) {
			fillHand();
		}
	}
	
	public void fillHand() {
		for(int v=0;v<5;v++) {
			cards.addCard(randomCard());
		}
	}
	
	public static Card randomCard() {
		int suit=(int)(Math.random()*4);
		int val=(int)(Math.random()*13)+1;
		return(new Card(val,suit));
	}

	public void deal(Card c){
		cards.addCard(c);
	}

	//Returns an array of Cards that the Player wishes to discard.
	//The game engine will call deal() on this player for each card
	//that exists in the return value. MS2 Instructions: Print the hand to
	//the terminal using System.out.println and ask the user which cards 
	//they would like to discard. The user will first the number of cards they
    //wish to discard, followed by the indices, one at a time, of
	//the card(s) they would like to discard, 
	//Return an array with the appropriate Card objects
	//that have been discarded, and remove the Card objects from this
	//player's hand. Use IO.readInt() for all inputs. In cases of error
	//re-ask the user for input.
	//
	// Example call to discard():
	//
	// This is your hand:
	// 0: Ace of Hearts
	// 1: 2 of Diamonds
	// 2: 5 of Hearts
	// 3: Jack of Spades
	// 4: Ace of Clubs
	// How many cards would you like to discard?
	// 2
	// 1
	// 2
	//
	// The resultant array will contain the 2 of Diamonds and the 5 of hearts in that order
	// This player's hand will now only have 3 cards
	public Card[] discard(){
		System.out.println("This is your hand:");
		for(int f=0;f<cards.getCardCount();f++) {
			System.out.println(""+f+": "+cards.getCard(f).toString());
		}
		System.out.println("How many cards would you like to discard?");
		int bye=IO.readInt();
		if(bye>cards.getCardCount()||bye<0) {
			System.out.println("Invalid Input");
			return(discard());
		}
		Card[] discarded=new Card[bye];
		for(int c=0;c<bye;c++) {
			System.out.println("Input the Indexes of the cards you'd like to discard");
			for(int f=0;f<5;f++) {
				if(cards.getCard(f)!=null) {
					System.out.println(""+f+": "+cards.getCard(f).toString());
				}else {
					System.out.println(""+f+": This Card is null");
				}
			}
			int inp=IO.readInt();
			if(inp>=cards.getCardCount()||inp<0) {
				System.out.println("Index out of Range. Try Again");
				c--;
			}else if(cards.getCard(inp)==null) {
				System.out.println("The Card at Index "+inp+" has already been removed");
				c--;
				/*
				 * this is where we put code for if the
				 * player enters the same input multiple times
				 */
			}else {
				discarded[c]=cards.getCard(inp);
				cards.removeCard(inp);
				//cards.sortByValue();
			}
		}
		cards.sortByValue();
		return(discarded);
	}

	//Returns the amount that this player would like to wager, returns
	//-1.0 to fold hand. Any non zero wager should immediately be deducted
	//from this player's balance. This player's balance can never be below
	// 0 at any time. This player's wager must be >= to the parameter min
	// MS2 Instructions: Show the user the minimum bet via the terminal 
	//(System.out.println), and ask the user for their wager. Use
	//IO.readDouble() for input. In cases of error re-ask the user for 
	//input.
	// 
	// Example call to wager()
	//
	// How much do you want to wager?
	// 200
	//
	// This will result in this player's balance reduced by 200
	
	public double wager(double min){
		System.out.println("Minimum Bet is "+min+" dollars");
		System.out.println("You Have "+bal+" Dollars");
		System.out.println("Input -1 to Fold");
		System.out.println("How much do you want to wager?");
		double bet=IO.readDouble();
		if(bet==-1.0) {
			folded=true;
			System.out.println("You Have Folded!");
			return(-1.0);
		}else if(bet<min) {
			System.out.println("Wager Too Low. Try Again");
			return(wager(min));
		}else if(bet>bal) {
			System.out.println("Not Enough Money. Try Again");
			return(wager(min));
		}else {
			bal-=bet;
			betAmount=bet;
			return(bet);
		}
	}

	//Returns this player's hand
	public Hand showHand(){
		return(cards);
	}

	//Returns this player's current balance
	public double getBalance(){
		return(bal);
	}

	//Increase player's balance by the amount specified in the parameter,
	//then reset player's hand in preparation for next round. Amount will
	//be 0 if player has lost hand
	public void winnings(double amount){
		bal+=amount;
		cards=new Hand();
	}
	
	public void winnings(double amount, boolean fill){
		bal+=amount;
		cards=new Hand();
		if(fill) {
			fillHand();
		}
	}
	
	public static void main(String[] args) {
		   Player p=new Player(10.0,true);
		   p.cards.printHand();
		   
		   System.out.println(p.discard());
		   System.out.println(p.discard());
		   /*p.deal(randomCard());
		   Card[] hos=new Card[40];
		   for(int k=0;k<hos.length;k++) {
			   hos[k]=randomCard();
			   //System.out.println(hos[k]);
			   p.deal(randomCard());
		   }
		   p.cards.printHand();
		   Card[] x =p.discard();
		   for(int h=0;h<x.length;h++) {
			   System.out.println(x[h]);
		   }
		   System.out.println(p.cards.getCardCount());
		   System.out.println(p.cards.updateAndGetCardCount());
		   //Card[] y=p.discard();
		   //System.out.println(p.wager(9.0));
		    * */
		    
	}

}