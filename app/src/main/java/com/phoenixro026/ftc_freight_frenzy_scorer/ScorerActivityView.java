package com.phoenixro026.ftc_freight_frenzy_scorer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.phoenixro026.ftc_freight_frenzy_scorer.database.Match;
import com.phoenixro026.ftc_freight_frenzy_scorer.databinding.ActivityScorerViewBinding;
import com.phoenixro026.ftc_freight_frenzy_scorer.recycleview.MatchViewModel;

import java.util.List;
import java.util.Locale;

public class ScorerActivityView extends AppCompatActivity{

    protected ActivityScorerViewBinding binding;

    public String teamColor;

    ///Autonomous
    public int autoTotalPoints = 0;
    public boolean duckDelivery = false;
    public int autoStorage = 0;
    public int autoHub = 0;
    public boolean freightBonus = false;
    public boolean teamElementUsed = false;
    public boolean autoParkedInStorage = false;
    public boolean autoParkedInWarehouse = false;
    public boolean autoParkedFully = false;

    ///Driver
    public int driverTotalPoints = 0;
    public int driverStorage = 0;
    public int driverHubL1 = 0;
    public int driverHubL2 = 0;
    public int driverHubL3 = 0;
    public int driverShared = 0;

    ///Endgame
    public int endgameTotalPoints = 0;
    public int carouselDucks = 0;
    public boolean balancedShipping = false;
    public boolean leaningShared = false;
    public boolean endgameParked = false;
    public boolean endgameFullyParked = false;
    public int capping = 0;

    //Penalties
    public int penaltiesTotal = 0;
    public int penaltiesMinor = 0;
    public int penaltiesMajor = 0;

    //Total
    public int totalPoints = 0;

    String key;
    int matchId;
    List<Match> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScorerViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Vibrator myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        MatchViewModel mMatchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
            matchId = extras.getInt("id") - 1;
            //The key argument here must match that used in the other activity
        }

        mMatchViewModel.getAllMatches().observe(this, newMatchList -> {
            // Update the cached copy of the words in the adapter.
            matchList = newMatchList;
            if(key.contentEquals("edit"))
                InsertValues(view);
        });

        binding.buttonEdit.setOnClickListener(v -> {
            myVib.vibrate(20);
            String value="edit";
            startActivity(new Intent(ScorerActivityView.this, ScorerActivity.class).putExtra("key", value).putExtra("id", matchId + 1));
        });
    }

    public void InsertValues(View view) {
        binding.textTeamName.setText(matchList.get(matchId).teamName);
        binding.textTeamCode.setText(matchList.get(matchId).teamCode);
        if(matchList.get(matchId).teamColor.contentEquals("red")) {
            binding.buttonTeamRed.setTextAppearance(view.getContext(), R.style.button_theme);
            binding.buttonTeamRed.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.button_shape_red));
            teamColor = "red";
        }else {
            binding.buttonTeamBlue.setTextAppearance(view.getContext(), R.style.button_theme);
            binding.buttonTeamBlue.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.button_shape_blue));
            teamColor = "blue";
        }

        ///Autonomous
        autoTotalPoints = matchList.get(matchId).autoTotalPoints;
        duckDelivery = matchList.get(matchId).duckDelivery;
        autoStorage = matchList.get(matchId).autoStorage;
        autoHub = matchList.get(matchId).autoHub;
        freightBonus = matchList.get(matchId).freightBonus;
        teamElementUsed = matchList.get(matchId).teamElementUsed;
        autoParkedInStorage = matchList.get(matchId).autoParkedInStorage;
        autoParkedInWarehouse = matchList.get(matchId).autoParkedInWarehouse;
        autoParkedFully = matchList.get(matchId).autoParkedFully;

        ///Driver
        driverTotalPoints = matchList.get(matchId).driverTotalPoints;
        driverStorage = matchList.get(matchId).driverStorage;
        driverHubL1 = matchList.get(matchId).driverHubL1;
        driverHubL2 = matchList.get(matchId).driverHubL2;
        driverHubL3 = matchList.get(matchId).driverHubL3;
        driverShared = matchList.get(matchId).driverShared;

        ///Endgame
        endgameTotalPoints = matchList.get(matchId).endgameTotalPoints;
        carouselDucks = matchList.get(matchId).carouselDucks;
        balancedShipping = matchList.get(matchId).balancedShipping;
        leaningShared = matchList.get(matchId).leaningShared;
        endgameParked = matchList.get(matchId).endgameParked;
        endgameFullyParked = matchList.get(matchId).endgameFullyParked;
        capping = matchList.get(matchId).capping;

        //Penalties
        penaltiesTotal = matchList.get(matchId).penaltiesTotal;
        penaltiesMinor = matchList.get(matchId).penaltiesMinor;
        penaltiesMajor = matchList.get(matchId).penaltiesMajor;

        //Total
        totalPoints = matchList.get(matchId).totalPoints;

        //Autonomous
        binding.textAutoTotalPointsNr.setText(String.format(Locale.US,"%d", autoTotalPoints));
        if(duckDelivery)
            binding.duckDeliveryText.setText(R.string.yes);
        else binding.duckDeliveryText.setText(R.string.no);
        binding.autoFreightStorageNr.setText(String.format(Locale.US,"%d", autoStorage));
        binding.autoFreightHubNr.setText(String.format(Locale.US,"%d", autoHub));
        if(freightBonus){
            binding.autoFreightBonusText.setText(R.string.yes);
            binding.teamElementUsed.setVisibility(View.VISIBLE);
            binding.teamElementUsedText.setVisibility(View.VISIBLE);
            if(teamElementUsed)
                binding.teamElementUsedText.setText(R.string.yes);
            else binding.teamElementUsedText.setText(R.string.no);
        }else {
            binding.autoFreightBonusText.setText(R.string.no);
            binding.teamElementUsed.setVisibility(View.GONE);
            binding.teamElementUsedText.setVisibility(View.GONE);
        }
        if(autoParkedInStorage || autoParkedInWarehouse){
            if(autoParkedInStorage)
                binding.autoParkedText.setText(R.string.storage);
            else binding.autoParkedText.setText(R.string.warehouse);
            binding.autoFullyParked.setVisibility(View.VISIBLE);
            binding.autoFullyParkedText.setVisibility(View.VISIBLE);
            if(autoParkedFully)
                binding.autoFullyParkedText.setText(R.string.yes);
            else binding.autoFullyParkedText.setText(R.string.no);
        } else {
            binding.autoParkedText.setText(R.string.no);
            binding.autoFullyParked.setVisibility(View.GONE);
            binding.autoFullyParkedText.setVisibility(View.GONE);
        }

        //Driver

        binding.textDriverTotalPointsNr.setText(String.format(Locale.US, "%d", driverTotalPoints));
        binding.textDriverStorageNr.setText(String.format(Locale.US,"%d", driverStorage));
        binding.textDriverHubL1Nr.setText(String.format(Locale.US,"%d", driverHubL1));
        binding.textDriverHubL2Nr.setText(String.format(Locale.US,"%d", driverHubL2));
        binding.textDriverHubL3Nr.setText(String.format(Locale.US,"%d", driverHubL3));
        binding.textDriverSharedNr.setText(String.format(Locale.US,"%d", driverShared));

        //Endgame
        binding.textEndgameTotalPointsNr.setText(String.format(Locale.US, "%d", endgameTotalPoints));
        binding.textEndgameCarouselNr.setText(String.format(Locale.US,"%d", carouselDucks));
        if(balancedShipping)
            binding.switchEndgameBalancedShippingText.setText(R.string.yes);
        else binding.switchEndgameBalancedShippingText.setText(R.string.no);
        if(leaningShared)
            binding.switchEndgameLeaningSharedText.setText(R.string.yes);
        else binding.switchEndgameLeaningSharedText.setText(R.string.no);
        if(endgameParked){
            if(endgameFullyParked)
                binding.switchEndgameParkedText.setText(R.string.fully);
            else binding.switchEndgameParkedText.setText(R.string.partly);
        } else binding.switchEndgameParkedText.setText(R.string.no);
        binding.textEndgameCappingNr.setText(String.format(Locale.US,"%d", capping));

        //Penalties
        binding.textPenaltiesNr.setText(String.format(Locale.US, "%d", penaltiesTotal));
        binding.textPenaltiesMinorNr.setText(String.format(Locale.US,"%d", penaltiesMinor));
        binding.textPenaltiesMajorNr.setText(String.format(Locale.US,"%d", penaltiesMajor));
        binding.textTotalNr.setText(String.format(Locale.US, "%d", totalPoints));

    }
}
