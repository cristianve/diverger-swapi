import * as React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

export default function CircularIndeterminate() {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginY: '20px' }}>
      <CircularProgress />
      <Typography variant="body1" sx={{ marginTop: '10px' }}>Loading all characters...</Typography>
    </Box>
  );
}