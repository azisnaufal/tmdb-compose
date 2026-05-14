# Phase 1

## Local DB
Setup this project with SQLDelight library. Then, create a new table to put users favorite movies. 
The `favorite` table should have this structure:
- id
- genre
- title
- poster_path

## Detail Screen
Update the current implementation of detail screen. This screen should receive the `movie_id`.
Then show loading indicator. While showing loading indicator it should fetch the movie detail using this endpoint:
```
curl --request GET \
     --url 'https://api.themoviedb.org/3/movie/movie_id?language=en-US' \
     --header 'accept: application/json'
```
After the movie detail is finish loading, hide the loading indicator then show the movie info.

The detail screen structure follow this list:
- Back button at the top left, and heart button at the top right. 
- The heart button turn unfilled when it does not exist in the `favorite` table. Set filled with red if the movie id is exist at `favorite` table.
- The top background should same as is (poster image with black gradient at the bottom around 20%).
- The bottom of poster image should display movie title with white text color over the gradient of poster image.
- Below of the movie title, display the movie vote indicator. Use the attribute of `vote_average` I let you design for this view component.
- Below the movie vote indicator show the movie `overview`

This screen should be able to use android or ios back button/gesture.