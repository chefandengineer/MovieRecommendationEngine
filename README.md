An Improved Movie Recommendation Engine 
=======================================

Rating Algorithms
-----------------
* Basic Rating Algorithm 
For the first two basic rating algorithms, the similarity metrics is calculated using dot product similarity as well as the cosine similarity metrics. The prediction is made using weight sum mean ratings, and no normalization is performed for the first two experiment. During the experiment, the first thing I found tricky is that the choice of K can bring very different results and also lead to different approaches to calculate the neighborhood. If the K is too small, there is very high chance that the top K neighbor does not have any rating for the particular movie or user, therefore no rating can be calculated for this query. While if we have too large K, many users or movies that are not similar is included in the neighborhood therefore provided a less precise result. Therefore, in the experiment, I took the K neighbor as the K movie or user that has the rating for the particular query, and the experiment shows the best performance comparing to other solutions. Another interesting finding for the first two experiment is that the simplest similarity metrics Dot product actually gives a better performance than the cosine similarity metrics.
For the third experiment, the problem is that different users may have different level of tolerance when giving ratings to the movies, some users may have a very happy personality and willing to give a high rate to any movie he has watched. For some other users, they may keep their rating very low, and give high rating to only a few movies. Therefore, the user-bias and movie-bias exists in the training set, normalizations are needed to better use the training set data. To solve this problem, this first thing come to my mind is using the average rating of different user or movie as the base, and using the deviation of the rating over the average rating as the measurement when we are calculating the the similarity as well as making the sum weight rating predictions. 
* Customzied Algorithm 
For the custom algorithms, I calculated the similarity metrics using Pearson Correlation
Coefficient. Also, based on the result of the first three experiment, there are other places needed to be improved in order to better predict the ratings of the user. For Pearson Correlation Coefficient does not take into consideration of how many movies or users that a pair of users
or movies share in common, while in many situations, the number of common movies or users
￼
does tell a lot of the similarity. For example, if user A has shared a lot common movies with user B, say 400 movies. While user A has shared only 5 movies with user C. In this situation, however, PCC may give A and C a higher similarity as it does not care how many the share in common. Therefore, in the custom algorithm, by replacing the metrics algorithm to Pearson evaluation, I also added some solution to penalize the similarity of those users or movies
that has less common rating items.
