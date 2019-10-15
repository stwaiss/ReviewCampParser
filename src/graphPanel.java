import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javafx.application.*;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.*;



public class graphPanel extends JFXPanel {
	private static String productType;

	public graphPanel() {

	}

	public void setProductType(String type) {
		if(type != null && type != "") {
			productType = type;
		}
	}





	//*******************************************************************************************************
	public static class totalStarRatingListener extends Application implements ActionListener {		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				try {

					//Boilerplate code for popup window for graph
					Platform.runLater(new Runnable() {
						public void run() {
							try {
								start(new Stage());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Stage Error");
					e1.printStackTrace();
				}
			}
			else {
				return;
			}


		}

		@Override
		public void start(Stage stage) throws Exception {
			// TODO Auto-generated method stub

			//create labels for each bar in the bar graph
			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			stage.setTitle("Total Star Ratings");

			//create new Bar Chart with X and Y Axis
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);

			//set labels for chart and axis
			bc.setTitle("Total Star Ratings");
			xAxis.setLabel("Star Rating");
			yAxis.setLabel("Count");

			//create new series to hold data
			XYChart.Series series1 = new XYChart.Series();
			series1.setName("Series 1");

			//set data for each bar by pulling from stats panel
			series1.getData().add(new XYChart.Data(starLabels[0], Integer.parseInt(statsPanel.starStatTextFields[6].getText())));
			series1.getData().add(new XYChart.Data(starLabels[1], Integer.parseInt(statsPanel.starStatTextFields[5].getText())));
			series1.getData().add(new XYChart.Data(starLabels[2], Integer.parseInt(statsPanel.starStatTextFields[4].getText())));
			series1.getData().add(new XYChart.Data(starLabels[3], Integer.parseInt(statsPanel.starStatTextFields[3].getText())));
			series1.getData().add(new XYChart.Data(starLabels[4], Integer.parseInt(statsPanel.starStatTextFields[2].getText())));

			//add series one to bar chart, and put bar chart into scene and stage
			bc.getData().addAll(series1);
			
			Scene scene = new Scene(bc, 1000,500);
			stage.setScene(scene);
			stage.show();

		}
	}

	//*******************************************************************************************************************

	public static class starRatingVsYearListener implements ActionListener{		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				try {
					Platform.runLater(new Runnable() {
						public void run() {
							try {
								start(new Stage());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Stage Error");
					e1.printStackTrace();
				}
			}
			else {
				return;
			}


		}

		public void start(Stage stage) throws Exception {
			// TODO Auto-generated method stub

			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			stage.setTitle("Total Star Ratings");
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);


			bc.setTitle("Star Ratings vs Year");
			xAxis.setLabel("Star Rating");
			yAxis.setLabel("Count");

			// pull the years from year distribution
			String[][] yearDistribution = statsPanel.yearDistribution();

			// create a year x 6 array to hold star ratings per year
			int[][] reviewsPerYear = new int[5][6];

			//iterate through array and assign the years into the reviewsPerYear, year column
			for(int i = 0; i < 5; i++) {
				if(yearDistribution[i][0] != null) {
					reviewsPerYear[i][0] = Integer.valueOf(yearDistribution[i][0]);
				}
			}

			//Save the master list of parsed reviews locally
			List<Review> allReviewsList = Parser.getReviews();

			//Iterate over all the years
			for(int i = 0; i < 5; i++) {
				//Iterate over all the reviews
				for(Review r : allReviewsList) {

					//Pull the year out from the date of the current review
					String thisReviewsYear = r.getDate().split("-")[0];

					//check to see if the year from this current review is the same as the year under analysis
					if(Integer.valueOf(thisReviewsYear) == reviewsPerYear[i][0]) {

						//switch case depending on value, and then add to that count
						double thisStarDouble = Double.valueOf(r.getStar());
						int thisStarInt = (int) thisStarDouble;
						switch(thisStarInt) {
						case 1:
							reviewsPerYear[i][1]++;
							break;
						case 2:
							reviewsPerYear[i][2]++;
							break;
						case 3:
							reviewsPerYear[i][3]++;
							break;
						case 4:
							reviewsPerYear[i][4]++;
							break;
						case 5:
							reviewsPerYear[i][5]++;
							break;
						default:
							break;
						}//end switch case
					}//end if
				}//end inner loop

				XYChart.Series series = new XYChart.Series();
				series.setName(String.valueOf(reviewsPerYear[i][0]));
				series.getData().add(new XYChart.Data(starLabels[0], reviewsPerYear[i][1]));
				series.getData().add(new XYChart.Data(starLabels[1], reviewsPerYear[i][2]));
				series.getData().add(new XYChart.Data(starLabels[2], reviewsPerYear[i][3]));
				series.getData().add(new XYChart.Data(starLabels[3], reviewsPerYear[i][4]));
				series.getData().add(new XYChart.Data(starLabels[4], reviewsPerYear[i][5]));
				bc.getData().addAll(series);
			}//end outer loop


			Scene scene = new Scene(bc, 1000,500);
			stage.setScene(scene);
			stage.show();

		}
	}

	//*************************************************************************************************************

	public static class starRatingVsSellerListener implements ActionListener{		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				try {
					Platform.runLater(new Runnable() {
						public void run() {
							try {
								start(new Stage());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Stage Error");
					e1.printStackTrace();
				}
			}
			else {
				return;
			}


		}

		public void start(Stage stage) throws Exception {
			// TODO Auto-generated method stub

			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			stage.setTitle("Total Star Ratings");
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);


			bc.setTitle("Star Ratings vs Seller");
			xAxis.setLabel("Star Rating");
			yAxis.setLabel("Count");

			// pull the years from year distribution
			String[][] sellerDistribution = statsPanel.sellerDistribution();

			// create a Seller x 6 array to hold star ratings per Seller
			String[][] reviewsPerSeller = {
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"},
					{null,"0","0","0","0","0"},
			};

			//iterate through array and assign the Sellers into the reviewsPerSeller, Seller column
			for(int i = 0; i < 5; i++) {
				if(sellerDistribution[i][0] != null) {
					reviewsPerSeller[i][0] = sellerDistribution[i][0];
					
				}
			}

			//Save the master list of parsed reviews locally
			List<Review> allReviewsList = Parser.getReviews();

			//Iterate over all the Sellers
			for(int i = 0; i < 5; i++) {
				if(reviewsPerSeller[i][0] != null) {
					//Iterate over all the reviews
					for(Review r : allReviewsList) {

						//Pull the Seller out from the date of the current review
						String thisReviewsSeller = r.getSeller();

						//check to see if the year from this current review is the same as the year under analysis
						if(thisReviewsSeller == reviewsPerSeller[i][0]) {

							//switch case depending on value, and then add to that count
							double thisStarDouble = Double.valueOf(r.getStar());
							int thisStarInt = (int) thisStarDouble;

							int oldValue = 0;
							int newValue = 0;

							/*	Since this is a string array, pull the old string out, convert to int
							 *  increment the old value and save, convert new value to string
							 *  and save new value into array
							 */ 

							switch(thisStarInt) {
							case 1:
								oldValue = Integer.valueOf(reviewsPerSeller[i][1]);
								newValue = oldValue + 1;
								reviewsPerSeller[i][1] = String.valueOf(newValue);
								break;
							case 2:
								oldValue = Integer.valueOf(reviewsPerSeller[i][2]);
								newValue = oldValue + 1;
								reviewsPerSeller[i][2] = String.valueOf(newValue);
								break;
							case 3:
								oldValue = Integer.valueOf(reviewsPerSeller[i][3]);
								newValue = oldValue + 1;
								reviewsPerSeller[i][3] = String.valueOf(newValue);
								break;
							case 4:
								oldValue = Integer.valueOf(reviewsPerSeller[i][4]);
								newValue = oldValue + 1;
								reviewsPerSeller[i][4] = String.valueOf(newValue);
								break;
							case 5:
								oldValue = Integer.valueOf(reviewsPerSeller[i][5]);
								newValue = oldValue + 1;
								reviewsPerSeller[i][5] = String.valueOf(newValue);
								break;
							default:
								break;
							}//end switch case
						}//end if
					}//end inner loop

					XYChart.Series series = new XYChart.Series();
					series.setName(String.valueOf(reviewsPerSeller[i][0]));
					series.getData().add(new XYChart.Data(starLabels[0], Integer.valueOf(reviewsPerSeller[i][1])));
					series.getData().add(new XYChart.Data(starLabels[1], Integer.valueOf(reviewsPerSeller[i][2])));
					series.getData().add(new XYChart.Data(starLabels[2], Integer.valueOf(reviewsPerSeller[i][3])));
					series.getData().add(new XYChart.Data(starLabels[3], Integer.valueOf(reviewsPerSeller[i][4])));
					series.getData().add(new XYChart.Data(starLabels[4], Integer.valueOf(reviewsPerSeller[i][5])));
					bc.getData().addAll(series);
				}//end outer if
			}//end outer loop



			Scene scene = new Scene(bc, 1000,500);
			stage.setScene(scene);
			stage.show();

		}
	}

	//************************************************************************************
	public static class keywordParetoListener implements ActionListener{		
		public void actionPerformed(ActionEvent e) {

			//Only do something if there are reviews in the system
			if(Parser.getReviews().size() != 0) {
				//System.out.println("Doing something");
				try {
					Platform.runLater(new Runnable() {
						public void run() {
							try {
								start(new Stage());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Stage Error");
					e1.printStackTrace();
				}
			}
			else {
				return;
			}


		}

		public void start(Stage stage) throws Exception {
			// TODO Auto-generated method stub

			final String[] starLabels = {"1 Star", "2 Star","3 Star", "4 Star", "5 Star"};
			stage.setTitle("Total Star Ratings");
			final CategoryAxis xAxis = new CategoryAxis();
			final NumberAxis yAxis = new NumberAxis();
			final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);


			bc.setTitle("Star Ratings vs Seller");
			xAxis.setLabel("Star Rating");
			yAxis.setLabel("Count");

			// pull the years from year distribution
			String[][] sellerDistribution = statsPanel.sellerDistribution();

			// create a year x 6 array to hold star ratings per year
			int[][] reviewsPerSeller = new int[5][6];

			//iterate through array and assign the years into the reviewsPerYear, year column
			for(int i = 0; i < 5; i++) {
				if(sellerDistribution[i][0] != null) {
					reviewsPerSeller[i][0] = Integer.valueOf(sellerDistribution[i][0]);
				}
			}

			//Save the master list of parsed reviews locally
			List<Review> allReviewsList = Parser.getReviews();

			//Iterate over all the years
			for(int i = 0; i < 5; i++) {
				//Iterate over all the reviews
				for(Review r : allReviewsList) {

					//Pull the year out from the date of the current review
					String thisReviewsYear = r.getDate().split("-")[0];

					//check to see if the year from this current review is the same as the year under analysis
					if(Integer.valueOf(thisReviewsYear) == reviewsPerSeller[i][0]) {

						//switch case depending on value, and then add to that count
						double thisStarDouble = Double.valueOf(r.getStar());
						int thisStarInt = (int) thisStarDouble;
						switch(thisStarInt) {
						case 1:
							reviewsPerSeller[i][1]++;
							break;
						case 2:
							reviewsPerSeller[i][2]++;
							break;
						case 3:
							reviewsPerSeller[i][3]++;
							break;
						case 4:
							reviewsPerSeller[i][4]++;
							break;
						case 5:
							reviewsPerSeller[i][5]++;
							break;
						default:
							break;
						}//end switch case
					}//end if
				}//end inner loop

				XYChart.Series series = new XYChart.Series();
				series.setName(String.valueOf(reviewsPerSeller[i][0]));
				series.getData().add(new XYChart.Data(starLabels[0], reviewsPerSeller[i][1]));
				series.getData().add(new XYChart.Data(starLabels[1], reviewsPerSeller[i][2]));
				series.getData().add(new XYChart.Data(starLabels[2], reviewsPerSeller[i][3]));
				series.getData().add(new XYChart.Data(starLabels[3], reviewsPerSeller[i][4]));
				series.getData().add(new XYChart.Data(starLabels[4], reviewsPerSeller[i][5]));
				bc.getData().addAll(series);
			}//end outer loop


			Scene scene = new Scene(bc, 1000,500);
			stage.setScene(scene);
			stage.show();

		}
	}
}
