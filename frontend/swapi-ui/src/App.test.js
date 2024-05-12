import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import App from './App';

describe('App', () => {
  it('renders search character text', () => {
    render(<App />);
    const searchCharacterText = screen.getByText(/Search Character/i);
    expect(searchCharacterText).toBeInTheDocument();
  });

  it('performs character search', async () => {
    render(<App />);
    const searchInput = screen.getByLabelText(/Search Character/i);
    fireEvent.change(searchInput, { target: { value: 'Luke Skywalker' } });
    fireEvent.click(screen.getByText(/Search/i));

    // Wait for the character card to appear
    await waitFor(() => {
      const characterName = screen.getByText(/Luke Skywalker/i);
      expect(characterName).toBeInTheDocument();
    });
  });

  it('clears search', async () => {
    render(<App />);
    const searchInput = screen.getByLabelText(/Search Character/i);
    fireEvent.change(searchInput, { target: { value: 'Luke Skywalker' } });
    fireEvent.click(screen.getByText(/Search/i));

    // Wait for the character card to appear
    await waitFor(() => {
      const characterName = screen.getByText(/Luke Skywalker/i);
      expect(characterName).toBeInTheDocument();
    });

    fireEvent.click(screen.getByText(/Clear/i));

    // Ensure search input is empty and character card is removed
    expect(searchInput).toHaveValue('');
    expect(screen.queryByText(/Luke Skywalker/i)).not.toBeInTheDocument();
  });

  // You can continue writing similar test cases for other functionalities like handling errors, loading all characters, etc.
});
