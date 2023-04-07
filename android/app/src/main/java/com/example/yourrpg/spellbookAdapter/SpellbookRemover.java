package com.example.yourrpg.spellbookAdapter;

import com.example.yourrpg.questlogAdapter.QuestlogViewHolderAdaptable;

/**
 * Interfejs do zapenienia funkcjonalności usunięcia elementu historii
 */
public interface SpellbookRemover
{
    /**
     * Usunięcie elementu z adaptera nie zapewnia usunięcia go z historii auta
     * Należy więc usunąć też element z historii auta
     * @param viewHolderAdaptable Pojedynczy element historii auta np. z adaptera
     */
    void remove(SpellbookViewHolderAdaptable viewHolderAdaptable);
}
