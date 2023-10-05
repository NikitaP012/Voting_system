package VS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VotingManagementSystem 

{
	
	
	static Scanner sc = new Scanner(System.in);
	
	
	
	
	
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException 
	{
		VotingManagementSystem vs = new VotingManagementSystem ();
		
		do{
			System.out.println("***** Add the voter details ***** ");
			vs.voter();
			
			
			System.out.println("Do you want the more voter");
	        String c = sc.next().toUpperCase();
			
			
			
			if(c.equals("Y")){
				continue;
			}
			
			
			else {
				System.out.println("***** Add the candidate details *****");
				vs.candidate();
				
				System.out.println("Do you want more candidate");
				String c1=sc.next().toUpperCase();
				
				
				if(c1.equals("Y")) {
					continue;
				}
				
				
				
				else {
					System.out.println("***** Start election *****");
					vs.start_election();
//					vs.result();
					
					System.out.println("Do you want terminate the election");
					String c2=sc.next();
					
					if(c2.equalsIgnoreCase("N")) {
						continue;
					}
					
					else {
						// vs.result();
						break;
					}
				
				}
			}
		}while(true);
		
		
		
		
		
		
	}

//   ADD THE VOTER DETAILS


	 void voter() throws ClassNotFoundException, SQLException {
		 
		 
		 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system" , "root" , "Nikita@12");
			Statement stmt = con.createStatement();
			
			
			
			try {
				
				System.out.println("Enter the voter id");
				int id=sc.nextInt();
				
				System.out.println("Enter the voter name");
				String name=sc.next();
				
				System.out.println("Enter the voter Address");
				String add=sc.next();
				
				System.out.println("Enter the voter password");
				String pass=sc.next();
				
				
				stmt.execute("insert into voter_detail( Voter_ID, Name, Address ,Password, Status ) VALUES("+id+" , ' "+name+" ' , ' "+add+" ' , "+pass+" , ' "+0+" ')");
				System.out.println(" ");
				System.out.println("Successfully added voter's deatils...");
						
				
			}
			
			catch(Exception e){
				System.out.println(e);
				
			}
		 
		
		
	}
	 
	 
	 
	 
	 //   ADD THE CANDIDATE DETAILS
void candidate() throws ClassNotFoundException, SQLException {
		 
		 
		 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system" , "root" , "Nikita@12");
			Statement stmt = con.createStatement();
			
			
			
			try {
				System.out.println("Enter the candidate id");
				int id=sc.nextInt();
				
				System.out.println("Enter the candidate name");
				String name=sc.next();
				
				System.out.println("Enter the candidate Address");
				String add=sc.next();
				
				
				
				stmt.execute("insert into candidate_detail( Candidate_ID, Name, Address , Votes ) VALUES("+id+" , ' "+name+" ' , ' "+add+" ' , ' "+0+" ')");
				System.out.println(" ");
				System.out.println("Successfully added candidate's deatils...");
				
			}
			
			
			catch(Exception e){
				System.out.println(e);
				
			}
			
	
		 
		
		
	}


//START THE ELECTION 


void start_election() throws ClassNotFoundException, SQLException {
	 
	 
	 Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system" , "root" , "Nikita@12");
		Statement stmt = con.createStatement();
		
		try {
			System.out.println("***** Start Election *****");
			System.out.println("  ");
			
			System.out.println("Enter the voter ID");
			int vid=sc.nextInt();
			
			
			System.out.println("Enter the voter password");
			String pw=sc.next();
			
			ResultSet rs = stmt.executeQuery("select * from voter_detail");
			
			while (rs.next()) {
				System.out.println("**** Valid id and password");

			    System.out.println("**** Show the candidate ****");

			    ResultSet candidate = stmt.executeQuery("select * from candidate_detail");

			    System.out.println("*** These are available candidates ***");
			    System.out.println("*** Vote for ***");

			    while(candidate.next()){
			        System.out.println("Candidate ID: "+candidate.getInt("Candidate_ID") +"   " + "Candidate Name: "+candidate.getString("Name") );    
			        System.out.println(" ");
			    }

			    try {
			        System.out.println("Enter Candidate ID:");
			        int cid = sc.nextInt();
			        PreparedStatement ps, vs = null;
			        ps = con.prepareStatement("UPDATE candidate_detail SET Votes=Votes+1 WHERE candidate_ID=?");
			        ps.setInt(1, cid);
			        ps.executeUpdate();
			        System.out.println("Your vote is updated successfully... ");
			        vs = con.prepareStatement("Update voter_detail SET Status=1 WHERE Voter_ID=?");
			        vs.setInt(1, vid);
			        vs.executeUpdate();
			    } catch(Exception e) {
			        System.out.println(e);
			    }
			}
			
				}
		      catch(Exception e){
			   System.out.println(e);
			
		}
		
		
		
		// Show the result
		}

void result() throws ClassNotFoundException, SQLException{
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting_system" , "root" , "Nikita@12");
	Statement stmt = con.createStatement();
			
	ResultSet r = stmt.executeQuery("SELECT ID, Name, Votes FROM candidate_detail ORDER BY Votes DESC");
	while(r.next())
	{
	System.out.println("Candidate ID: "+r.getInt("Candidate_ID") +"   " + "Candidate Name: "+r.getString("Name") + " " + "Candidate Votes: "+r.getInt("Votes") );	
	System.out.println(" ");	
	}
	r = stmt.executeQuery("SELECT ID, Name, Votes FROM candidate_detail ORDER BY Votes DESC");
	r.next();
	System.out.println("  ");
	System.out.println("******************** WINNER ********************");
	System.out.println("Candidate ID: "+r.getInt("Candidate_ID") +"   " + "Candidate Name: "+r.getString("Name") + " " + "Candidate Votes: "+r.getInt("Votes"));
	
	r.close();
	
}



	 
	 
	
	
	
	
	
	
}