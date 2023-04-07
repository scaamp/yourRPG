package com.example.yourrpg.questlogAdapter;

import com.example.yourrpg.spellbookAdapter.SpellbookViewHolderAdaptable;

/**
 * Interfejs do zapenienia funkcjonalności usunięcia elementu historii
 */
public interface QuestlogRemover
{
    /**
     * Usunięcie elementu z adaptera nie zapewnia usunięcia go z historii auta
     * Należy więc usunąć też element z historii auta
     * @param viewHolderAdaptable Pojedynczy element historii auta np. z adaptera
     */
    void remove(QuestlogViewHolderAdaptable viewHolderAdaptable);
}
