package team6.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import team6.entity.Review;

public class ReviewDAO {
	private MongoDatabase db;
	
	public ReviewDAO() {
		db = MongoDBDatabaseOperator.INSTANCE.getMongoDatabase();
	}

	public void insertReview(Review review) {
		MongoCollection<Document> reviewCollection = db.getCollection("review");
		Document doc = new Document();
		
		doc	.append("hotel-name", review.getHotelName())
			.append("hotel-address", review.getHotelAddress())
			.append("hotel-city", review.getHotelCity())
			.append("hotel-state", review.getHotelState())
			.append("hotel-zip", review.getHotelZip())
			.append("room-name", review.getRoomName())
		;
		
		Date checkInDate = Date.from(review.getCheckInDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date checkOutDate = Date.from(review.getCheckOutDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date reviewDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		doc	.append("check-in-date", checkInDate)
			.append("check-out-date", checkOutDate)
			.append("review-date", reviewDate)
		;
		
		doc	.append("first-name", review.getFirstName())
			.append("last-name", review.getLastName())
			.append("rating", review.getRating())
			.append("comment", review.getComment())
		;
		
		reviewCollection.insertOne(doc);
	}
	
}
