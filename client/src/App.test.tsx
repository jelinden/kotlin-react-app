import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders coffee list link', () => {
  render(<App />);
  const element = screen.getByText(/Coffee list/i);
  expect(element).toBeInTheDocument();
});
