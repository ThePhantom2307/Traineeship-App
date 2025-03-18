package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="evaluations")
public class Evaluation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer evaluationId;
	
	@Enumerated(EnumType.STRING)
	private EvaluationType evaluationType;
	
	@Column(name="motivation")
	private Integer motivation;
	
	@Column(name="efficiency")
	private Integer efficiency;
	
	@Column(name="effectiveness")
	private Integer effectiveness;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "traineeship_position_id")
    private TraineeshipPosition traineeshipPosition;
	
	public Evaluation() {}

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public EvaluationType getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(EvaluationType evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Integer getMotivation() {
        return motivation;
    }

    public void setMotivation(Integer motivation) {
        this.motivation = motivation;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public Integer getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(Integer effectiveness) {
        this.effectiveness = effectiveness;
    }

    public TraineeshipPosition getTraineeshipPosition() {
        return traineeshipPosition;
    }

    public void setTraineeshipPosition(TraineeshipPosition traineeshipPosition) {
        this.traineeshipPosition = traineeshipPosition;
    }
}
