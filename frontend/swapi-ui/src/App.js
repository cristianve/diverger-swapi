import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import CharacterCard from './CharacterCard';
import DenseAppBar from './DenseAppBar'
import Footer from './Footer'
import CircularProgress from './CircularProgress'

function App() {
  const [searchTerm, setSearchTerm] = useState('');
  const [character, setCharacter] = useState(null);
  const [allCharacters, setAllCharacters] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const API_URL = process.env.REACT_APP_API_URL; // Assuming you set this variable in your environment

  const loadAllCharacters = useCallback(async () => {
    try {
      setLoading(true);
      const allCharactersList = [
        'Luke Skywalker',
        'C-3PO',
        'R2-D2',
        'Darth Vader',
        'Leia Organa',
        'Owen Lars',
        'Beru Whitesun lars',
        'R5-D4',
        'Biggs Darklighter',
        'Obi-Wan Kenobi'
      ];
      const charactersData = await Promise.all(allCharactersList.map(async characterName => {
        const response = await axios.get(`${API_URL}/swapi-proxy/person-info?name=${encodeURIComponent(characterName)}`);
        return response.data;
      }));
      setAllCharacters(charactersData);
    } catch (error) {
      console.error('Error fetching all characters:', error);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  }, [API_URL]);

  useEffect(() => {
    loadAllCharacters();
  }, [loadAllCharacters]);

  const handleSearch = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${API_URL}/swapi-proxy/person-info?name=${encodeURIComponent(searchTerm)}`);
      setCharacter(response.data);
      setError(null); // Clear any previous errors
    } catch (error) {
      console.error('Error fetching character:', error);
      setCharacter(null);
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleClearSearch = () => {
    setSearchTerm('');
    setCharacter(null);
    setError(null); // Clear any previous errors
  };

  return (
    <div style={{ padding: 20 }}>
      <DenseAppBar/>
      <Typography variant="h4" component="h1" gutterBottom style={{ marginBottom: 20, color: 'blue' }}>
        Search Character
      </Typography>

      <TextField
        label="Search Character"
        variant="outlined"
        fullWidth
        value={searchTerm}
        onChange={handleInputChange}
        style={{ marginBottom: 20 }}
      />
      <Button variant="contained" onClick={handleSearch} style={{ marginRight: 10 }} disabled={loading}>
        Search
      </Button>
      <Button variant="contained" onClick={handleClearSearch} disabled={loading}>
        Clear
      </Button>

      {loading &&  <CircularProgress/>}

      {error && (
        <p style={{ color: 'red' }}>{error}</p>
      )}

      {character && (
        <>
          <hr />
          <Typography variant="h5" component="h2" gutterBottom style={{ color: 'blue' }}>
            Searched Character
          </Typography>
          <CharacterCard character={character} />
        </>
      )}

      {allCharacters.length > 0 && (
        <>
          <hr />
          <Typography variant="h4" component="h1" gutterBottom style={{ marginBottom: 20, color: 'blue' }}>
            All Characters
          </Typography>
          <Grid container spacing={2}>
            {allCharacters.map((char, index) => (
              <CharacterCard key={index} character={char} />
            ))}
          </Grid>
        </>
      )}
      <Footer/>
    </div>
  );
}

export default App;
