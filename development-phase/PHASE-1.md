# Phase 1

## Splash screen 

The splash screen should show a "TMDb" text in the center of the screen. The text should be colored white and the background should be dark.
The splash screen should visible for 2 seconds then automatically redirect to login screen.

## Login Screen

This simple login page consist of: 
- Text `Welcome to TMBd`
- Input Field Username
- Input Field Password
- Button Login 

Those view should be placed at the center of the screen. 
When login button is clicked, it will validate the username and password. If the username and password is `admin` and `admin` then redirect the screen to main screen (List of genre and it's popular movies). If is not, then show info the credentials is wrong. 

## Main Screen (List genre and their trending movies)

When system access this screen. Show loading at the top with red primary netflix color. 
This loading should show whenever fetching data is still in progress.
This viewModel code should fetch all genres using this endpoint:

> curl --request GET \
> --url 'https://api.themoviedb.org/3/genre/movie/list?language=en' \
> --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGM2ZWEzZGI2ODBmYWU1MDI0MTZkODBmZjQxNmEwYiIsIm5iZiI6MTUzNTYxMDY4Ni4wMjIsInN1YiI6IjViODc4ZjNlOTI1MTQxMWZkZTAwMjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MiVcLYgIQdOgmMWr_iJ_7QEevrOdz47MLK85GAQvGEo' \
> --header 'accept: application/json'

Populate all the genres name to the screen with style of red primary netflix color. Then every genre has horizontal grid of popular movies. 
Use this endpoint to get the popular movies for every genre. Pass the id of genre to parameter of `with_genres`:

> curl --request GET \
> --url 'https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&with_genres=12' \
> --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzOGM2ZWEzZGI2ODBmYWU1MDI0MTZkODBmZjQxNmEwYiIsIm5iZiI6MTUzNTYxMDY4Ni4wMjIsInN1YiI6IjViODc4ZjNlOTI1MTQxMWZkZTAwMjQxZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MiVcLYgIQdOgmMWr_iJ_7QEevrOdz47MLK85GAQvGEo' \
> --header 'accept: application/json'

After fetching the popular movies, then render the image of `poster_path` using coil image library. 
The base url of image to show the `poster_path` is `https://image.tmdb.org/t/p/w300{poster_path}`.
Usually the `poster_path` already contains the slash at initial of string. So we just replace the {poster_path} to the base url.



### Search movies and genre
TBA

### Pagination
TBA