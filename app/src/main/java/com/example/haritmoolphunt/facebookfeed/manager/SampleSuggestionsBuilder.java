package com.example.haritmoolphunt.facebookfeed.manager;

import android.content.Context;

import com.example.haritmoolphunt.facebookfeed.fragment.UserProfileFragment;

import org.cryse.widget.persistentsearch.SearchItem;
import org.cryse.widget.persistentsearch.SearchSuggestionsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Harit Moolphunt on 24/2/2561.
 */

public class SampleSuggestionsBuilder implements SearchSuggestionsBuilder {
    private Context mContext;
    private List<SearchItem> mHistorySuggestions = new ArrayList<SearchItem>();
    String[] nameList = {"Mahnmook","Music","Mint","Ant","Ae","Fame","Pada","Petch","Proud","Pim","Sonja","Anny","Nink","Orn"};
    String[] nameId = {"1705672169741865","108567926503749","138188866783330","445003689233126","709046175950703","1913673758896179","260045927831231","108001133246382","120406398606162","799444370230832","111090532897541","809228849236630","333542507094850","737460709751015"};

    public SampleSuggestionsBuilder(Context context) {
        this.mContext = context;
        createHistorys();
    }

    private void createHistorys() {
        for(int i=0;i<nameList.length;i++) {
            mHistorySuggestions.add(new SearchItem(nameList[i], nameList[i], SearchItem.TYPE_SEARCH_ITEM_SUGGESTION));
        }
        FeedListManager.getInstance().setmHistorySuggestions(nameList,nameId);
    }

    @Override
    public Collection<SearchItem> buildEmptySearchSuggestion(int maxCount) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        items.addAll(mHistorySuggestions);
        return items;
    }

    @Override
    public Collection<SearchItem> buildSearchSuggestion(int maxCount, String query) {
        List<SearchItem> items = new ArrayList<SearchItem>();

        for(SearchItem item : mHistorySuggestions) {
            if(item.getTitle().startsWith(query.substring(0,1).toUpperCase()+query.substring(1))) {
                items.add(item);
            }
        }
        return items;
    }
}