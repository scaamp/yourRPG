package com.example.yourrpg.adapter;

import com.example.yourrpg.model.ViewHolderAdaptable;

/**
 * Interfejs do zapenienia funkcjonalności usunięcia elementu historii
 */
public interface HistoryRemover
{
    /**
     * Usunięcie elementu z adaptera nie zapewnia usunięcia go z historii auta
     * Należy więc usunąć też element z historii auta
     * @param viewHolderAdaptable Pojedynczy element historii auta np. z adaptera
     */
    void remove(ViewHolderAdaptable viewHolderAdaptable);
}
