import React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

function CharacterCard({ character }) {
    return (

      <Grid item xs={12} sm={6} md={4}>
        <Card>
          <CardContent>
            <Typography variant="h5" component="h2" gutterBottom>
              {character.name}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              Birth Year: {character.birth_year}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              Gender: {character.gender}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              Planet: {character.planet_name}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              Fastest Vehicle: {character.fastest_vehicle_driven}
            </Typography>
            <Typography variant="body2" color="textSecondary" component="p">
              Films:
            </Typography>
            <ul>
              {character.films.map((film, index) => (
                <li key={index}>{film.title} ({film.release_date})</li>
              ))}
            </ul>
          </CardContent>
        </Card>
      </Grid>
    );
  }


export default CharacterCard;
